package br.org.bueno.application.socket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "age")
	private int age;

	@Column(name = "heigth")
	private int heigth;

	@Column(name = "weight")
	private int weight;

	@Column(name = "nameLegth")
	private int nameLegth;

	public User() {

	}

	public User(String name, int age, int heigth, int weight, int nameLegth) {
		this.name = name;
		this.age = age;
		this.heigth = heigth;
		this.weight = weight;
		this.nameLegth = nameLegth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public int getNameLegth() {
		return nameLegth;
	}

	public void setNameLegth(int nameLegth) {
		this.nameLegth = nameLegth;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", heigth=" + heigth + ", weight=" + weight
				+ ", nameLegth=" + nameLegth + "]";
	}

}
