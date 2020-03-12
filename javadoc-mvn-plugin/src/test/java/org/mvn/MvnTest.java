//package org.mvn;
//
//import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
//import org.apache.maven.cli.CliRequest;
//import org.apache.maven.cli.MavenCli;
//import org.apache.maven.execution.MavenSession;
//import org.apache.maven.model.Model;
//import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
//import org.apache.maven.project.DefaultProjectBuildingRequest;
//import org.apache.maven.project.MavenProject;
//import org.apache.maven.project.ProjectBuildingRequest;
//import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;
//import org.apache.maven.shared.dependency.graph.internal.DefaultDependencyGraphBuilder;
//import org.apache.maven.shared.utils.logging.MessageUtils;
//import org.codehaus.plexus.classworlds.ClassWorld;
//import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.InvocationTargetException;
//
///**
// * Created with IntelliJ IDEA.
// * Description:
// * @author genx
// * @date 2020/3/12 14:13
// */
//public class MvnTest {
//
//    @Test
//    public void test() throws IOException, XmlPullParserException {
//
//        FileInputStream fis = new FileInputStream(new File("E:\\github-workspace\\javadoc-help\\javadoc-demo/pom.xml"));
//        MavenXpp3Reader reader = new MavenXpp3Reader();
//        Model model = reader.read(fis);
//
//
//        MavenProject mavenProject = new MavenProject(model);
//
//
//        MavenSession session = null;
//
//        System.out.println(mavenProject);
//
//
////        for (Dependency dependency : mavenProject.getDependencies()) {
////            System.out.println(dependency);
////        }
//
//        DependencyGraphBuilder dependencyGraphBuilder = new DefaultDependencyGraphBuilder();
//
//
//        ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(session.getProjectBuildingRequest());
//
//        buildingRequest.setProject(mavenProject);
//
//        ArtifactFilter artifactFilter = null;
//
////        DependencyNode rootNode = dependencyGraphBuilder.buildDependencyGraph(buildingRequest, artifactFilter, reactorProjects);
//
//
////        org.apache.maven.cli.MavenCli
//    }
//
//
//    @Test
//    public void test2() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
//        System.setProperty("maven.multiModuleProjectDirectory", "E:\\github-workspace\\javadoc-help\\javadoc-demo");
//
//        System.setProperty("maven.home", "D:\\Program Files\\JetBrains\\IntelliJ IDEA 2019.1.1\\plugins\\maven\\lib\\maven3");
//        System.setProperty("classworlds.conf", "D:\\Program Files\\JetBrains\\IntelliJ IDEA 2019.1.1\\plugins\\maven\\lib\\maven3\\bin\\m2.conf");
//
//
//        String[] args = {
//                "-Didea.version2019.1.1",
//                "-DskipTests=true",
//                "javadoc-mvn:javaDoc"
//        };
//
//        ClassWorld classWorld = null;
//
//
//        MavenCli cli = new MavenCli();
//        MessageUtils.systemInstall();
//        MessageUtils.registerShutdownHook();
//
//        Constructor c = org.apache.maven.cli.CliRequest.class.getDeclaredConstructor(String[].class, org.codehaus.plexus.classworlds.ClassWorld.class);//获取有参构造
//        c.setAccessible(true);
//        CliRequest cliRequest = (CliRequest) c.newInstance(args, classWorld);    //通过有参构造创建对象
//
//        int result = cli.doMain(cliRequest);
//        MessageUtils.systemUninstall();
//        System.out.println(result);
//    }
//
//
//}
