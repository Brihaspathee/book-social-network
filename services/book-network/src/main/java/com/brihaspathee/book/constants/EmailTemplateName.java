package com.brihaspathee.book.constants;

import lombok.Getter;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 2/22/25
 * Time: 10:17 AM
 * Project: book-social-network
 * Package Name: com.brihaspathee.book.constants
 * To change this template use File | Settings | File and Code Template
 */
@Getter
public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account");
    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
