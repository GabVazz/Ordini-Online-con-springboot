# OrdiniOnline_con_springboot
Applicazione web enterprise con spring boot che espone una serie di articoli all’utente e quest’ultimo può aggiungerli al carello e procedere con l’ordine.

- Progetto spring boot
- Database: MySQL 8.4
- Front end: html 5, css,bootstrap 3
- Server: Tomcat 9
- Test: JUnit 5

## Dependecy Utilizzate:

- Spring Data JPA
- Spring Thymeleaf
- Spring Security
- Spring Lombok
- Spring Web MVC

## Dettagli implementazioni:

- Creazione Model sfruttando le annotations @Entity, @Table, @Id, @GeneratedValue, @Column, @OneToMany, @ManyToOne, @OneToOne, @ManyToMany.

```java
package com.gab.ordini.businesscomponent.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Articolo implements Serializable {

	private static final long serialVersionUID = 5587935411214161769L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idArticolo;

	@Column(nullable = false)
	private String marca;
	@Column(nullable = false)
	private String modello;
	@Column(nullable = false)
	private double prezzo;
	@Column(columnDefinition = "tinyint(1) default 1")
	private boolean disponibile = true;
	
	@OneToMany(mappedBy = "articolo", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<OrdineArticolo> oa = new HashSet<OrdineArticolo>();

	public long getIdArticolo() {
		return idArticolo;
	}

	public void setIdArticolo(long idArticolo) {
		this.idArticolo = idArticolo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public boolean isDisponibile() {
		return disponibile;
	}

	public void setDisponibile(boolean disponibile) {
		this.disponibile = disponibile;
	}

	public Set<OrdineArticolo> getOa() {
		return oa;
	}

	public void setOa(Set<OrdineArticolo> oa) {
		this.oa = oa;
	}

	@Override
	public String toString() {
		return "Articolo [idArticolo=" + idArticolo + ", marca=" + marca + ", modello=" + modello + ", prezzo=" + prezzo
				+ ", disponibile=" + disponibile + ", oa=" + oa + "]";
	}
}

```

- La proprietà `oa` di tipo `Set<OrdineArticolo>` è annotata con `@JsonIgnore`. Questo significa che quando l'oggetto `Articolo` viene serializzato in JSON, la proprietà `oa` sarà ignorata e non apparirà nell'output JSON.
L'uso di `@JsonIgnore` è particolarmente utile in situazioni in cui si desidera evitare ricorsioni o cicli infiniti durante la serializzazione di oggetti che hanno relazioni bidirezionali, come nel caso di una relazione `@OneToMany` dove un `Articolo` è associato a uno o più `OrdineArticolo`, e viceversa.

- Le tabelle del db vengano create automaticamente attraverso Hibernate.
- I parametri di configurazione sia del db, sia del progetto vengono inseriti all’interno dell’ “application.properties”
- Utilizzo del BCryptPasswordEncoder per criptare le password degli utenti
- Utilizzo un classe “LeafConfig” che permette di usare spring security direttamente dal front end con thymeleaf

```java
package com.gab.ordini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
public class LeafConfig {
	// con questa classe posso usare spring security direttamente dal front end con
	// thymeleaf
	@Bean
	SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}
}

```

- La spring security è stata implementata in modo tale che tutte le richieste di autenticazione sono autorizzate da richieste con url “/admin/** e ruolo “ADMIN”
- Mentre i login normali vengono autorizzati per tutti gli altri

```java
@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// Configura le autorizzazioni delle richieste
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().permitAll())

				.formLogin(form -> form.loginPage("/loginAdmin").permitAll())

				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logoutAdmin"))
						.logoutSuccessUrl("/admin/"))
				// Configura l'autenticazione di base HTTP
				.httpBasic(withDefaults());

		// Costruisce e ritorna l'oggetto HttpSecurity configurato
		return http.build();
	}
```

- Implementazione delle repository per ogni model, i quali estendono le interfaccie di JPA repository
- In alcune sono state usate anche annotation come @Transactional e @Modifying

```java
package com.gab.ordini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gab.ordini.businesscomponent.model.Articolo;

import jakarta.transaction.Transactional;

@Repository("articoloRepository")
public interface ArticoloRepository extends JpaRepository<Articolo, Long> {
	@Query(value = "select * from articolo where disponibile = 1", nativeQuery = true)
	public List<Articolo> getArticoliDisponibili();
	
	@Query(value = "select id_articolo from ordine_articolo group by id_articolo"
			+ " having sum(qta) >= all(select sum(qta) somma from ordine_articolo group by id_articolo)", nativeQuery = true)
	public List<Long> getArticoliPiuVenduto();
	
	@Modifying
	@Transactional
	@Query(value = "update articolo set disponibile = 1 where id_articolo = ?1", nativeQuery = true)
	public void disponibile(long id);
	
	@Modifying
	@Transactional
	@Query(value = "update articolo set disponibile = 0 where id_articolo = ?1", nativeQuery = true)
	public void nonDisponibile(long id);
	
	
}
```

- **Transazionalità**: L'annotazione `@Transactional` garantisce che le operazioni di modifica del database siano eseguite all'interno di una transazione. Se qualcosa va storto durante l'operazione, la transazione può essere annullata per evitare modifiche parziali.
- **Modifying**: L'annotazione `@Modifying` è necessaria per le query `update` o `delete`, altrimenti Spring Data JPA si aspetta che la query sia di tipo `select` e tenterebbe di mappare il risultato su entità, il che non è possibile per query di aggiornamento o eliminazione.

- Implementazione dei service e dei serviceImplementation per ogni model con la relativa annotation “*@Service”*

- Implementazione di due controller che gestiscono le varie richieste:
    - ClientController
    - AdminController
- Entrambi sfruttano l’oggetto “ModelAndView” che descrive sia i model sia il renderizzamento della view, ossia la pagina alla quale l’applicazione deve essere indirizzata.
