package com.parking.rest.hateoas.asm;

import com.parking.core.models.entities.AccountGroup;
import com.rest.mvc.AccountGroupController;
import com.rest.resources.AccountGroupResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class AccountGroupResourceAsm extends ResourceAssemblerSupport<AccountGroup, AccountGroupResource> {
    public AccountGroupResourceAsm() {
        super(AccountGroupController.class, AccountGroupResource.class);
    }

    @Override
    public AccountGroupResource toResource(AccountGroup accountGroup) {
        AccountGroupResource res = new AccountGroupResource();

        res.setGroupName(accountGroup.getGroupName());
        res.setGroupDescription(accountGroup.getGroupDesc());

        res.add(linkTo(AccountGroupController.class).slash(accountGroup.getId()).withSelfRel());
        res.add(linkTo(AccountGroupController.class).slash(accountGroup.getId()).slash("account-entries").withRel("entries"));

        res.setRid(accountGroup.getId());

//        res.add(linkTo(methodOn(AccountController.class).findAllAccounts(account.getId())).withRel("blogs"));

//        if (accountGroup.getAccounts() != null)  {
//            res.add(linkTo(AccountController.class).slash(group.get().getId()).withRel("owner"));
//        }
        return res;
    }
}
