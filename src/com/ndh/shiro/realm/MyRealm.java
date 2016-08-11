package com.ndh.shiro.realm;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

@Component
public class MyRealm extends AuthorizingRealm {

	/**
	 * 授权方法
	 * 1.实际返回的是SimpleAuthorizationInfo的实例
	 * 2.可以调用SimpleAuthorizationInfo的addRole来添加当前登录user的权限信息
	 * 3.可以调用PrincipalCollection参数的getPrimaryPrincipal方法来获取用户信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Object principal = principalCollection.getPrimaryPrincipal();
		
		if("admin".equals(principal)){	
			info.addRole("admin");
		}
		
		if ("user".equals(principal)) {
			info.addRole("list");
		}
		
		info.addRole("user");
		return info;
	}

	/**
	 * 认证方法
	 * 1.编写表单：表单的action和username、password的参数都是什么？
	 * 回答：提交到你想提交的地方，username和password参数名都任意即可
	 * 2.提交表单：例如，提交到了SpringMVC的handler
	 * ①获取用户名和密码
	 * ②Subject currentUser = SecurityUtils.getSubject();
	 * UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
	 * currentUser.login(token);
	 * 3.当Subject调用login方法时，即会触发doGetAuthenticationInfo，且把UsernamePasswordToken
	 * 对象传入，然后在该方法中执行真正的认证，访问数据库进行比对
	 * ①获取用户名和密码
	 * ②
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println(token.getPrincipal());
		System.out.println(token.getCredentials());
		
		//1.从token中获取登录的username，不需要获取password
		
		//2.利用username查询数据库得到用户的信息
		
		//3.创建SimpleAuthenticationInfo对象并返回，注意该对象的凭证是从数据库中查询得到的
		//而不是页面输入的，实际的密码校验可以交由Shiro完成
		
		/**
		 *4.关于密码加密的事：shiro的密码加密可以非常非常复杂，但是实现起来却非常简单
		 *①可以选择加密方法：在当前的realm中编写一个public类型的不带参数的的方法
		 *使用@PostConstruct注解进行修饰，在其中来设置密码的匹配方式.
		 *②设置盐值：一般从数据库中查询获取
		 */
		
		//登录的主信息：可以是一个实体类的对象，但该实体类的对象一定是根据token的username查询得到的
		Object principal = token.getPrincipal();
		//认证信息：从数据库中查询出来的信息，密码的比对交给shiro
		//String credentials = "123456";
		String credentials = "68f3139a38b232392cc9d3b6ddd762f7";
		//设置盐值
		String source = "abcd";
		//当前Realm的name
		String realmName = getName();
		ByteSource credentialsSalt = new Md5Hash(source);
		SimpleAuthenticationInfo info = 
				new SimpleAuthenticationInfo(principal, credentials, 
						credentialsSalt, realmName);
				
		return info;
	}
	
	//@PostConstruct：相当于 bean节点的init-method配置.
	@PostConstruct
	public void setCredentialsMatcher(){	
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(1024);
		
		setCredentialsMatcher(credentialsMatcher);
	}
	
	public static void main(String[] args) {
		String saltSource = "abcd";
		
		String hashAlgorithmName = "MD5";
		String credentials = "123456";
		Object salt = new Md5Hash(saltSource);
		int hashIterations = 1024;
				
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
		System.out.println("68f3139a38b232392cc9d3b6ddd762f7".equals(result.toString()));
	}

}
