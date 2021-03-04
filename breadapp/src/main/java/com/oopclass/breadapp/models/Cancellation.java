package com.oopclass.breadapp.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Entity
@Table(name = "cancellations")
public class Cancellation implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    private String cancellationFullName;
    private String cancellationProductId;
    private String cancellationReason;
    private String urder;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCancellationFullName() {
        return cancellationFullName;
    }

    public void setCancellationFullName(String cancellationFullName) {
        this.cancellationFullName = cancellationFullName;
    }

    public String getCancellationProductId() {
        return cancellationProductId;
    }

    public void setCancellationProductId(String cancellationProductId) {
        this.cancellationProductId = cancellationProductId;
    }
    
    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
    
    public String getUrder() {
		return urder;
	}

	public void setUrder(String urder) {
		this.urder = urder;
	}
    
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt() {
        LocalDate localDate = LocalDate.now();
        this.createdAt = localDate;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.updatedAt = localDateTime;
    }
    
    @Override
    public String toString() {
        return "Cancellation [id=" + id + ", cancellationFullName=" + cancellationFullName +", cancellationProductId=" + cancellationProductId +", cancellationReason=" + cancellationReason + "]";
    }
}