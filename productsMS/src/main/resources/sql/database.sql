CREATE SCHEMA productms;

CREATE  TABLE productms.approval_queue_txn_seq ( 
	next_val             BIGINT       
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE  TABLE productms.product_chng_hist_txn ( 
	product_chng_record_id BIGINT  NOT NULL   AUTO_INCREMENT  PRIMARY KEY,
	product_id           BIGINT       ,
	product_chng_hist_event VARCHAR(255)       ,
	product_chng_hist_details VARCHAR(255)       ,
	product_chng_performed_by VARCHAR(255)       ,
	product_chng_performed_on DATETIME(6)       ,
	product_chng_status  INT  NOT NULL     
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX idx_product_chng_hist_txn ON productms.product_chng_hist_txn ( product_id );

CREATE  TABLE productms.product_chng_hist_txn_seq ( 
	next_val             BIGINT       
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE  TABLE productms.product_mst ( 
	product_id           BIGINT  NOT NULL   AUTO_INCREMENT  PRIMARY KEY,
	product_name         VARCHAR(255)       ,
	product_description  VARCHAR(255)       ,
	product_sku          VARCHAR(255)       ,
	product_catagory_name VARCHAR(255)       ,
	product_catalog_name VARCHAR(255)       ,
	product_price        DECIMAL(38,2)       ,
	product_status       INT  NOT NULL     ,
	product_activated_on DATETIME(6)       ,
	CONSTRAINT unq_product_mst UNIQUE ( product_name ) ,
	CONSTRAINT unq_product_mst_0 UNIQUE ( product_sku ) 
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX idx_product_mst ON productms.product_mst ( product_catagory_name );

CREATE INDEX idx_product_mst_1 ON productms.product_mst ( product_catalog_name );

CREATE  TABLE productms.product_mst_extn ( 
	product_extn_record_id BIGINT  NOT NULL   AUTO_INCREMENT  PRIMARY KEY,
	product_id           BIGINT       ,
	product_att_name     VARCHAR(255)       ,
	product_att_value    VARCHAR(255)       ,
	product_att_created_on DATETIME(6)       ,
	product_att_status   INT  NOT NULL     ,
	CONSTRAINT fk_product_mst_extn FOREIGN KEY ( product_id ) REFERENCES productms.product_mst( product_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX fk_product_mst_extn ON productms.product_mst_extn ( product_id );

CREATE  TABLE productms.product_mst_extn_seq ( 
	next_val             BIGINT       
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE  TABLE productms.product_mst_seq ( 
	next_val             BIGINT       
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE  TABLE productms.product_update_request_tmp ( 
	id                   BIGINT  NOT NULL     PRIMARY KEY,
	product_id           BIGINT       ,
	approval_id          BIGINT  NOT NULL     ,
	name                 VARCHAR(100)       ,
	`status`             BOOLEAN       ,
	price                DECIMAL(10,0)       ,
	is_active            INT   DEFAULT 1    
 ) engine=InnoDB;

CREATE  TABLE productms.approval_queue_txn ( 
	approval_id          BIGINT  NOT NULL   AUTO_INCREMENT  PRIMARY KEY,
	approval_req_type    VARCHAR(255)       ,
	approval_req_details BIGINT  NOT NULL     ,
	approval_req_on      DATETIME(6)       ,
	approval_approved_by VARCHAR(255)       ,
	approval_act_on      DATETIME(6)       ,
	approval_status      INT  NOT NULL     ,
	CONSTRAINT fk_approval_queue_txn FOREIGN KEY ( approval_req_details ) REFERENCES productms.product_chng_hist_txn( product_chng_record_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE INDEX idx_approval_queue_txn ON productms.approval_queue_txn ( approval_req_type, approval_status );

CREATE INDEX idx_approval_queue_txn_0 ON productms.approval_queue_txn ( approval_req_details );

CREATE  TABLE productms.product_att_chnged_hist_details ( 
	product_att_chng_d_id BIGINT  NOT NULL     PRIMARY KEY,
	product_att_chng_txn_id BIGINT       ,
	product_att          VARCHAR(100)       ,
	product_att_old_val  VARCHAR(400)       ,
	is_active            INT   DEFAULT 1    ,
	product_id           BIGINT       ,
	CONSTRAINT fk_product_att_chnged_hist_details FOREIGN KEY ( product_att_chng_txn_id ) REFERENCES productms.product_chng_hist_txn( product_chng_record_id ) ON DELETE NO ACTION ON UPDATE NO ACTION
 ) engine=InnoDB;

CREATE INDEX fk_product_att_chnged_hist_details ON productms.product_att_chnged_hist_details ( product_att_chng_txn_id );
