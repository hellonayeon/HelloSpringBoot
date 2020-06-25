package kr.ac.hansung.cse.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor

@Entity
@Table(name="productTable")
public class Product implements Serializable {

	/**
	 * send to client side in JSON format
	 **/
	private static final long serialVersionUID = -8613449854766783122L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productId")
	private int id;
	
	@NonNull
    private String name;
    
	@NonNull
    private String category;
    
    private int price;
    
	@NonNull
    private String manufacturer;
    
    private int unitInStock;
    
    private String description;
    
    public Product(Product product) {
    	this.name = product.name;
    	this.category = product.category;
    	this.price = product.price;
    	this.manufacturer = product.manufacturer;
    	this.unitInStock = product.unitInStock;
    	this.description = product.description;
    }

}
