package org.hexagonexample.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AccountId {
    private UUID id;

    public AccountId(String id) {
        this.id = UUID.fromString(id);
    }

    private AccountId(UUID id) {
        this.id = id;
    }

    public static AccountId generate() {
        return new AccountId(UUID.randomUUID());
    }

}
