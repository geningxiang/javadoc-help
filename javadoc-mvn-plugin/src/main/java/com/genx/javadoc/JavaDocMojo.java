package com.genx.javadoc;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;

import java.io.File;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/11 22:51
 */
@Mojo(name = "javaDoc", defaultPhase = LifecyclePhase.VERIFY)
public class JavaDocMojo extends AbstractMojo {


    @Parameter( defaultValue = "${session}", readonly = true )
    private MavenSession session;

    @Parameter( defaultValue = "${project}", readonly = true )
    private MavenProject project;

    @Parameter( defaultValue = "${mojoExecution}", readonly = true )
    private MojoExecution mojo;

    @Parameter( defaultValue = "${plugin}", readonly = true ) // Maven 3 only
    private PluginDescriptor plugin;

    @Parameter( defaultValue = "${settings}", readonly = true )
    private Settings settings;

    @Parameter( defaultValue = "${project.basedir}", readonly = true )
    private File basedir;

    @Parameter( defaultValue = "${project.build.directory}", readonly = true )
    private File target;


    @Parameter( defaultValue = "${project.build.sourceDirectory}", readonly = true )
    private File sourceDirectory;

    @Parameter( defaultValue = "${project.build.outputDirectory}", readonly = true )
    private File outputDirectory;


    @Parameter( defaultValue = "${build.outputDirectory}/${build.finalName}/WEB-INF/lib")
    private File libDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        System.out.println("JavaDocMojo start");

        System.out.println(basedir.getAbsolutePath());

        System.out.println(target.getAbsolutePath());

        System.out.println(sourceDirectory);

        System.out.println(outputDirectory);

        System.out.println(libDir);
    }
}
