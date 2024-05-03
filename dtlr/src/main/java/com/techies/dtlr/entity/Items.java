package com.techies.dtlr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Items")
public class Items {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "ItemNumber")
    private String itemNumber;
    
    @Column(name = "Name")
    private String name;
    
    @Column(name = "Description")
    private String description;
    
    @Column(name = "rstatus")
    private String rstatus;
    
    @Column(name = "current_app_status")
    private String currentAppStatus;
    
    @Column(name = "qunatity_avalable") // corrected the spelling
    private int quantityAvailable;
    
    @Column(name = "ext1")
    private String ext1;
    
    @Column(name = "ext2")
    private String ext2;
    
    @Column(name = "ext3")
    private String ext3;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the itemNumber
	 */
	public String getItemNumber() {
		return itemNumber;
	}

	/**
	 * @param itemNumber the itemNumber to set
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the rstatus
	 */
	public String getRstatus() {
		return rstatus;
	}

	/**
	 * @param rstatus the rstatus to set
	 */
	public void setRstatus(String rstatus) {
		this.rstatus = rstatus;
	}

	/**
	 * @return the currentAppStatus
	 */
	public String getCurrentAppStatus() {
		return currentAppStatus;
	}

	/**
	 * @param currentAppStatus the currentAppStatus to set
	 */
	public void setCurrentAppStatus(String currentAppStatus) {
		this.currentAppStatus = currentAppStatus;
	}

	/**
	 * @return the quantityAvailable
	 */
	public int getQuantityAvailable() {
		return quantityAvailable;
	}

	/**
	 * @param quantityAvailable the quantityAvailable to set
	 */
	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	/**
	 * @return the ext1
	 */
	public String getExt1() {
		return ext1;
	}

	/**
	 * @param ext1 the ext1 to set
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	 * @return the ext2
	 */
	public String getExt2() {
		return ext2;
	}

	/**
	 * @param ext2 the ext2 to set
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	/**
	 * @return the ext3
	 */
	public String getExt3() {
		return ext3;
	}

	/**
	 * @param ext3 the ext3 to set
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

    // Constructors, Getters, and Setters
}

