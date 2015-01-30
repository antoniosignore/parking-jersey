package com.parking.rest.hateoas.asm;

import com.parking.core.models.entities.Account;
import com.rest.mvc.AccountController;
import com.rest.resources.AccountResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class AccountResourceAsm extends ResourceAssemblerSupport<Account, AccountResource> {
    public AccountResourceAsm() {
        super(AccountController.class, AccountResource.class);
    }

    @Override
    public AccountResource toResource(Account account) {
        AccountResource res = new AccountResource();

        res.setName(account.getName());
        res.setPassword(account.getPassword());
        res.setRid(account.getId());

        res.add(linkTo(methodOn(AccountController.class).getAccount(account.getId())).withSelfRel());

        res.add(linkTo(methodOn(AccountController.class).findAllBlogs(account.getId())).withRel("blogs"));
        res.add(linkTo(methodOn(AccountController.class).findAllAccountGroups(account.getId())).withRel("accountGroups"));
        res.add(linkTo(methodOn(AccountController.class).findAllParkings(account.getId())).withRel("parkings"));
        res.add(linkTo(methodOn(AccountController.class).findAllConnections(account.getId())).withRel("connections"));
        res.add(linkTo(methodOn(AccountController.class).findAllVehicles(account.getId())).withRel("vehicles"));
        return res;
    }
}
