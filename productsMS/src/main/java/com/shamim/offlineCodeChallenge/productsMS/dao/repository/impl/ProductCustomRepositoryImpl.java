package com.shamim.offlineCodeChallenge.productsMS.dao.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.shamim.offlineCodeChallenge.productsMS.bo.ProductSearch;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductMst;
import com.shamim.offlineCodeChallenge.productsMS.dao.repository.ProductCustomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProductCustomRepositoryImpl implements ProductCustomRepository {

	@Autowired
	EntityManager entityManager;    

    public List<ProductMst> findByCustomSearch(ProductSearch ps) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductMst> cq = cb.createQuery(ProductMst.class);

        Root<ProductMst> product = cq.from(ProductMst.class);
        
        List<Predicate> predicates = new ArrayList<>();
        cb.desc(product.get("productActivatedOn"));
        predicates.add(cb.equal(product.get("productStatus"), ps.getOnlyActiveProduct()==1?1:ps.getOnlyActiveProduct()));
        // product name like
        if (ps.getProductName() != null) {
            predicates.add(cb.like(product.get("productName"), '%'+ps.getProductName()+'%'));
        }
        // product price btwn, ge, le
        if (ps.getMinPrice() != null && ps.getMaxPrice()!=null) {
            predicates.add(cb.between(product.get("productPrice"), ps.getMinPrice(),ps.getMaxPrice()));
        } else if (ps.getMinPrice() != null ) {
            predicates.add(cb.ge(product.get("productPrice"), ps.getMinPrice()));
        }else if ( ps.getMaxPrice()!=null) {
            predicates.add(cb.le(product.get("productPrice"), ps.getMaxPrice()));
        }
        
        if (ps.getMinPostedDate() != null && ps.getMaxPoistedDate()!=null) {
            predicates.add(cb.between(product.get("productActivatedOn"), ps.getMinPostedDate(),ps.getMaxPoistedDate()));
        } else if (ps.getMinPostedDate() != null ) {
            predicates.add(cb.greaterThan(product.get("productActivatedOn"), ps.getMinPostedDate()));
        }else if ( ps.getMaxPoistedDate()!=null) {
            predicates.add(cb.lessThan(product.get("productActivatedOn"), ps.getMaxPoistedDate()));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<ProductMst> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
