package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "tb_producto")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer prodId;
	
	@Column(length = 45, nullable = false)
	private String nomProd;
	
	@Column(nullable = false)
	private Double precioProd;
	
	@Column(nullable = false)
	private Integer stockProd;
	
	
	@ManyToOne
	@JoinColumn(name = "catId", nullable = false)
	private Categoria categoria;
}
