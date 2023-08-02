package com.shamim.offlineCodeChallenge.productsMS.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.shamim.offlineCodeChallenge.productsMS.dao.model.ApprovalQueueTxn;

import jakarta.transaction.Transactional;

@Repository
public interface ApprovalQueueRepository extends JpaRepository<ApprovalQueueTxn, Long>  {
	List<ApprovalQueueTxn> findByApprovalStatus(int approvalStatus);
	
	@Transactional
	@Modifying
	@Query("UPDATE ApprovalQueueTxn aqt SET aqt.approvalStatus=?2 WHERE aqt.approvalId=?1")
	int updateStatus(long approvalId,int approvalStatus);
}
