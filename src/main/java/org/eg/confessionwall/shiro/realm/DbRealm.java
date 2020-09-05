package org.eg.confessionwall.shiro.realm;

import com.sun.org.apache.regexp.internal.RE;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.eg.confessionwall.entity.User;
import org.eg.confessionwall.service.UserService;

import javax.annotation.Resource;

public class DbRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;

    @Override
    public String getName() {
        return "DbRealm";
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String account= (String) token.getPrincipal();
        User user=this.userService.queryByAccount(account);
        if (user == null) {//需要改，存在很大问题
//            throw new UnknownAccountException("账号不存在！");
            return null;
        }else if(user.getUserState()!=2){
            return null;
        }
        return new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
