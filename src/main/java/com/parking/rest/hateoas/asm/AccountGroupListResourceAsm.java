package com.parking.rest.hateoas.asm;

import com.parking.core.services.util.AccountGroupList;
import com.rest.mvc.AccountGroupController;
import com.rest.resources.AccountGroupListResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;


public class AccountGroupListResourceAsm extends ResourceAssemblerSupport<AccountGroupList, AccountGroupListResource> {

    public AccountGroupListResourceAsm() {
        super(AccountGroupController.class, AccountGroupListResource.class);
    }

    @Override
    public AccountGroupListResource toResource(AccountGroupList accountGroupList) {
        AccountGroupListResource res = new AccountGroupListResource();
        res.setGroups(new AccountGroupResourceAsm().toResources(accountGroupList.getAccountGroups()));
        return res;
    }
}
