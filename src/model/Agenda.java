package model;

import java.time.LocalDateTime;
import java.util.List;

public class Agenda {
	private Integer id;
	private Prestador prestador;
	private List<LocalDateTime> horario;
   
   public Agenda() {
	super();
   }

   
   public Integer getId() {
	return id;
   }
   public void setId(Integer id) {
	this.id = id;
   }


   public Prestador getPrestador() {
	return prestador;
   }

   public void setPrestador(Prestador prestador) {
	this.prestador = prestador;
   }

   public List<LocalDateTime> getHorario() {
	return horario;
   }

   public void setHorario(List<LocalDateTime> horario) {
	this.horario = horario;
   }
}
