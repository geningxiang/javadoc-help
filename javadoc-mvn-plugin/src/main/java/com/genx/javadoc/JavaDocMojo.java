package com.genx.javadoc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.genx.javadoc.helper.RestApiBuilder;
import com.genx.javadoc.utils.FileUtil;
import com.genx.javadoc.vo.ClassDocVO;
import com.genx.javadoc.vo.RestApiDoc;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.*;
import org.apache.maven.settings.Settings;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;
import org.apache.maven.shared.dependency.graph.DependencyNode;
import org.apache.maven.shared.dependency.graph.internal.DefaultDependencyGraphBuilder;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static com.genx.javadoc.utils.ZipUtil.unzip;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/11 22:51
 */
@Mojo(name = "javaDoc", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true,
        requiresDependencyResolution = ResolutionScope.COMPILE)
public class JavaDocMojo extends AbstractMojo {


    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    /**
     * Contains the full list of projects in the reactor.
     */
    @Parameter(defaultValue = "${reactorProjects}", readonly = true, required = true)
    private List<MavenProject> reactorProjects;

    @Parameter(defaultValue = "${mojoExecution}", readonly = true)
    private MojoExecution mojo;

    @Parameter(defaultValue = "${plugin}", readonly = true)
    private PluginDescriptor plugin;

    @Parameter(defaultValue = "${settings}", readonly = true)
    private Settings settings;

    @Parameter(defaultValue = "${project.basedir}", readonly = true)
    private File basedir;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true)
    private File target;


    @Parameter(defaultValue = "${project.build.sourceDirectory}", readonly = true)
    private File sourceDirectory;

    @Parameter(defaultValue = "${project.build.outputDirectory}", readonly = true)
    private File outputDirectory;


//    @Parameter(defaultValue = "${project.build.outputDirectory}/${project.build.finalName}/WEB-INF/lib")
//    private File libDir;


    @Parameter(defaultValue = "${project.compileClasspathElements}", readonly = true, required = true)
    private List<String> compilePath;

    @Parameter( property = "scope" )
    private String scope;


    /**
     * The dependency tree builder to use.
     */
    @Component( hint = "default" )
    private DependencyGraphBuilder dependencyGraphBuilder;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        System.out.println("JavaDocMojo start");


        System.out.println(basedir.getAbsolutePath());

        System.out.println(target.getAbsolutePath());

        System.out.println(sourceDirectory);

        System.out.println(outputDirectory);

//        System.out.println(libDir);

        System.out.println("compilePath:");
        for (String s : compilePath) {
            System.out.println(s);
        }

//        System.out.println("project.getArtifacts() = " + project.getArtifacts());
//
//        System.out.println("project.getDependencyArtifacts() = "+project.getDependencyArtifacts());

        System.out.println("project.getArtifacts() = " + project.getArtifacts());
        for (Artifact artifact : project.getArtifacts()) {
            System.out.println(artifact + " | " + artifact.getFile());

        }

        System.out.println("##############");

        try {


            ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest( session.getProjectBuildingRequest() );

            buildingRequest.setProject( project );

            ArtifactFilter artifactFilter = createResolvingArtifactFilter();

            // non-verbose mode use dependency graph component, which gives consistent results with Maven version
            // running
            DependencyNode rootNode =  dependencyGraphBuilder.buildDependencyGraph( buildingRequest, artifactFilter, reactorProjects );
            //获取依赖树结构
            System.out.println(JSONObject.toJSONString(rootNode));




        } catch (Exception e) {
            e.printStackTrace();
        }


//        try {
//            RepositorySystemSession session = (RepositorySystemSession) ProjectBuildingRequest.class.getMethod("getRepositorySession").invoke(buildingRequest);
//            System.out.println("## RepositorySystemSession ##");
//            System.out.println(session);
//
//            DependencyResolutionRequest request = new DefaultDependencyResolutionRequest();
//            request.setMavenProject(project);
//
//            DependencyResolutionRequest.class.getMethod("setRepositorySession", RepositorySystemSession.class).invoke(request, session);
//
////            //resolve
////            DependencyResolutionResult result = this.resolveDependencies(request, reactorProjects);
////            org.eclipse.aether.graph.DependencyNode graph = (org.eclipse.aether.graph.DependencyNode) DependencyResolutionResult.class.getMethod("getDependencyGraph").invoke(request);
////            System.out.println("graph = " + graph);
//
//
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }


        if (sourceDirectory == null || !sourceDirectory.exists()) {
            throw new MojoFailureException("sourceDirectory error : " + sourceDirectory);
        }

        if (outputDirectory == null || !outputDirectory.exists()) {
            throw new MojoFailureException("outputDirectory error : " + outputDirectory);
        }

        if (target == null || !target.exists()) {
            throw new MojoFailureException("target error : " + target);
        }

        Map<String, ClassDocVO> map = JavaDocReader.read(sourceDirectory, compilePath);

        File docDir = new File(target.getAbsolutePath() + "/docs");
        docDir.mkdirs();

        String json = JSONObject.toJSONString(map);
        File file = new File(target.getAbsolutePath() + "/docs/javadoc.json");
        FileUtil.writeFile(file, json);

        File file2 = new File(target.getAbsolutePath() + "/docs/javadoc.js");
        FileUtil.writeFile(file2, "var javadoc = " + json + ";");

        RestApiDoc restApiDoc = new RestApiBuilder()
                .setBaseUrl("测试环境", "http://192.168.1.100:8080/")
                .setBaseUrl("预发布环境", "http://a.b.c/")
                .analysisClassDocs(map.values()).build();


        File file3 = new File(target.getAbsolutePath() + "/docs/restApiData.js");
        FileUtil.writeFile(file3, "var restApiData = " + JSON.toJSON(restApiDoc) + ";");


        copyHtml(docDir);
    }

    private ArtifactFilter createResolvingArtifactFilter()
    {
        ArtifactFilter filter;

        // filter scope
        if ( scope != null )
        {
            getLog().debug( "+ Resolving dependency tree for scope '" + scope + "'" );

            filter = new ScopeArtifactFilter( scope );
        }
        else
        {
            filter = null;
        }

        return filter;
    }

    private void copyHtml(File dir) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:html.zip");
        if (resource.exists()) {
            try {
                unzip(resource.getInputStream(), dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("未找到 classpath:html.zip");
        }
    }
}
