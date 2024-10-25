package com.yosekit.creditmanager.repository;

import com.yosekit.creditmanager.model.Client;
import com.yosekit.creditmanager.repository.base.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepository extends BaseRepositoryImpl<Client, Long> {
}
