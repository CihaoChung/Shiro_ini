/**   
 * Copyright © 2020 eSunny Info. Tech Ltd. All rights reserved.
 * 
 * @Package: xyz.wadewhy.ShiroDemo01 
 * @author: 钟子豪   网站wadewhy.xyz
 * @date: 2020年3月10日 下午4:58:19 
 */
package xyz.wadewhy.ShiroDemo01.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
* @author 钟子豪
* 作者 E-mail:wadewhy@yeah.net
* @version 创建时间：2020年3月10日 下午4:58:19
* 类说明
*/
/** 
* @ClassName: MyRealm 
* @Description: TODO
* @author: wadewhy
* @date: 2020年3月10日 下午4:58:19  
*/
public class MyRealm extends AuthorizingRealm {

    public String getName() {
        return "myRealm";
    }

    public boolean supports(AuthenticationToken token) {
        // 仅支持UsernamePasswordToken类型的Token
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 得到用户名
        String username = (String) token.getPrincipal();
        // 得到密码
        String password = new String((char[]) token.getCredentials());
        if ("钟子豪".equals(username)) {
            if (!"123456".equals(password)) {
                // 密码不匹配
                throw new IncorrectCredentialsException();
            }
            // 如果身份认证验证成功，返回一个AuthenticationInfo实现；
            return new SimpleAuthenticationInfo(username, password, getName());
        } else if ("wadewhy".equals(username)) {
            if (!"123456".equals(password)) {
                // 密码不匹配
                throw new IncorrectCredentialsException();
            }
            // 如果身份认证验证成功，返回一个AuthenticationInfo实现；
            return new SimpleAuthenticationInfo(username, password, getName());
        } else {
            // 未知用户名错误
            throw new UnknownAccountException();
        }
        /*if (!"钟子豪".equals(username)) {// 模拟匹配用户名
            // 未知用户名错误
            throw new UnknownAccountException();
        }
        if (!"123456".equals(password)) {
            // 密码不匹配
            throw new IncorrectCredentialsException();
        }
        // 如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(username, password, getName());*/
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // TODO Auto-generated method stub
        // 从principals获取主身份信息
        // 将getPrimaryPrincipal方法返回值转为真实身份信息(在上边的doGetAuthecticationInfo认证通过填充到SimpleAuthenticationInfo)
        String userCode = (String) principals.getPrimaryPrincipal();
        // 根据信息获取权限信息
        // 连接数据库。。。
        // 模拟从数据库获取到数据
        List<String> permissions = null;
        List<String> roles = null;
        if ("钟子豪".equals(userCode)) {
            permissions = new ArrayList<String>();
            permissions.add("user:add");
            permissions.add("user:update");
            roles = new ArrayList<String>();
            roles.add("role1");
            roles.add("role2");
        } else if ("wadewhy".equals(userCode)) {

            permissions = new ArrayList<String>();
            permissions.add("user:update");
            roles = new ArrayList<String>();
            roles.add("role1");
        }

        // 注意，这里role1，role2角色我没有赋予任何权限，只是模拟存在这两个角色，实际上角色是要有相关权限

        // 查询到权限数据，返回
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 将上边查询到授权信息填充到simpleAuthorizationInfo对象中
        simpleAuthorizationInfo.addStringPermissions(permissions);
        simpleAuthorizationInfo.addRoles(roles);
        return simpleAuthorizationInfo;
    }

}
