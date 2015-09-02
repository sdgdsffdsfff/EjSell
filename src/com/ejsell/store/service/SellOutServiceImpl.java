package com.ejsell.store.service;

import org.springframework.stereotype.Service;

import com.ejsell.store.entity.SellOut;
import com.persistent.impl.Hibernate3CRUDImpl;

@Service
public class SellOutServiceImpl extends Hibernate3CRUDImpl<SellOut> implements SellOutService {

}
