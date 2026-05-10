package com.seng.management_system.repository;

import com.seng.management_system.model.AttachmentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttachmentFileRepository extends JpaRepository<AttachmentFile,Long>, JpaSpecificationExecutor<AttachmentFile> {
}
