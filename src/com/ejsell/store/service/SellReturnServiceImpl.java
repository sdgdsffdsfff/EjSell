package com.ejsell.store.service;

import org.springframework.stereotype.Service;

import com.ejsell.store.entity.SellReturn;
import com.persistent.impl.Hibernate3CRUDImpl;

@Service
public class SellReturnServiceImpl extends Hibernate3CRUDImpl<SellReturn> implements SellReturnService {

}
