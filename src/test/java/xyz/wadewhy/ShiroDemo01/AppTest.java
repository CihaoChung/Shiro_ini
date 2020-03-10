package xyz.wadewhy.ShiroDemo01;

import java.util.Scanner;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import junit.framework.Assert;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * 模拟用户认证，也就是用户登录 ,使用自带的realm
     * 1.根据配置文件shiro.ini创建SecurityManagerFactory
     * 2.通过工厂获取SecurityManager 
     * 3.将SecurityManager绑定到当前运行环境 
     * 4.从当前运行环境中得到Subject主体
     * 5.模拟构造Shiro登录数据 
     * 6.主体登录验证
     * 7.退出
     */
    @Test
    public void ShiroiniTest() {
        // 1.根据配置文件shiro.ini创建SecurityManagerFactory
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 2.通过工厂火圈SecurityManager
        SecurityManager securityManager = factory.getInstance();
        // 3.将SecurityManager绑定到当前运行环境
        SecurityUtils.setSecurityManager(securityManager);
        // 4.从当前运行环境中得到Subject主体
        Subject subject = SecurityUtils.getSubject();
        Scanner scanner = new Scanner(System.in);
        String username = null;
        String password = null;
        do {
            // 5.模拟构造Shiro登录数据
            System.out.println("输入用户名：");
            username = scanner.next();
            System.out.println("输入密码：");
            password = scanner.next();
            try {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                // 6.主体登录验证
                subject.login(token);
                System.err.println("登录成功=" + subject.isAuthenticated() + "，身份:" + subject.getPrincipal());

            } catch (AuthenticationException e) {
                System.err.println("登录失败=" + subject.isAuthenticated() + "，身份:" + subject.getPrincipal());
            }
            // 断言用户已经登录
            Assert.assertEquals(true, subject.isAuthenticated());
            // 7、退出
            subject.logout();

        } while (true);

    }

    /**
     * 得到Subject
     * @param shiroPath
     * @return
     */
    public Subject getSubject(String shiroPath) {
        // 1.根据配置文件shiro.ini创建SecurityManagerFactory
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:" + shiroPath);
        // 2.通过工厂火圈SecurityManager
        SecurityManager securityManager = factory.getInstance();
        // 3.将SecurityManager绑定到当前运行环境
        SecurityUtils.setSecurityManager(securityManager);
        // 4.从当前运行环境中得到Subject主体
        Subject subject = SecurityUtils.getSubject();
        return subject;
    }

    /**
     * 用于测试的方法
     * @param subject
     */
    public void Test(Subject subject) {
        String username = null;
        String password = null;
        Scanner scanner = new Scanner(System.in);
        do {
            // 5.模拟构造Shiro登录数据
            System.out.println("输入用户名：");
            username = scanner.nextLine();
            System.out.println("输入密码：");
            password = scanner.nextLine();
            try {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                // 6.主体登录验证
                subject.login(token);
                System.err.println("登录成功=" + subject.isAuthenticated() + "，身份:" + subject.getPrincipal());
                // 登录成功之后，完成授权
                // 授权：检验当前登录用户是否具有操作权限，是否具有某个角色
                System.err.println("是否有role2角色？" + subject.hasRole("role2"));
                System.err.println("是否有添加用户权限【user:add】" + subject.isPermitted("user:add"));

            } catch (AuthenticationException e) {
                System.err.println("登录失败=" + subject.isAuthenticated() + "，身份:" + subject.getPrincipal());
            }
            // 断言用户已经登录
            Assert.assertEquals(true, subject.isAuthenticated());
            // 7、退出
            subject.logout();

        } while (true);
    }

    /**
     * 使用自带realm
     */
    @Test
    public void ShiroiniTest1() {
        String shiroPath = "shiro.ini";
        // 得到subject
        Subject subject = getSubject(shiroPath);
        // 测试
        Test(subject);

    }

    /**
    * 使用自定义realm
    */
    @Test
    public void ShiroiniTest2() {
        String shiroPath = "shiro-realm.ini";
        // 得到subject
        Subject subject = getSubject(shiroPath);
        // 测试
        Test(subject);

    }

    /**
     * 授权演示，基于自带的realm
     */
    @Test
    public void ShiroiniTest3() {
        String shiroPath = "shiro-prem.ini";
        // 得到subject
        Subject subject = getSubject(shiroPath);
        // 测试
        Test(subject);
    }

    /**
     * 授权演示，基于自定义的realm
     */

    @Test
    public void ShiroiniTest4() {
        String shiroPath = "shiro-realm.ini";
        // 得到subject
        Subject subject = getSubject(shiroPath);
        // 测试
        Test(subject);
    }
}
