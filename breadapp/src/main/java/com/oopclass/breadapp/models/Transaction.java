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
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate apcreatedAt;
    @Column(name = "updated_at")
    private LocalDateTime apupdatedAt;
    


    private String apFullName;
    private String apRevision;
    private String apContactNo;
    private String apAddress;
    private LocalDate doa;
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
      public String getApFullName() {
        return apFullName;
    }
    public void setApFullName(String apFullName) {
        this.apFullName = apFullName;
    }
      public String getApRevision() {
        return apRevision;
    }
    public void setApRevision(String apRevision) {
        this.apRevision = apRevision;
    }
      public String getApContactNo() {
        return apContactNo;
    }
    public void setApContactNo(String apContactNo) {
        this.apContactNo = apContactNo;
    }
      public String getApAddress() {
        return apAddress;
    }
    public void setApAddress(String apAddress) {
        this.apAddress = apAddress;
    }

    
      public LocalDate getDoa() {
        return doa;
    }

    public void setDoa(LocalDate doa) {
        this.doa = doa;
    }
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    public LocalDate getCreatedAt() {
        return apcreatedAt;
    }

    public void setCreatedAt() {
        LocalDate localDate = LocalDate.now();
        this.apcreatedAt = localDate;
    }
    
    public LocalDateTime getUpdatedAt() {
        return apupdatedAt;
    }

    public void setUpdatedAt() {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.apupdatedAt = localDateTime;}

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", apFullName=" + apFullName + ", apRevision=" + apRevision + ", apContactNo=" + apContactNo + ", apAddress=" + apAddress + ", doa= " + doa + "]";
    }

}