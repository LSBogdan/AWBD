package com.ProjX.projxpersitance.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.Objects;

@MappedSuperclass
public abstract class BasicEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid2")
    @Column(name= "id" , unique = true , length = 36)
    protected String id;

    @Column(name = "created_on")
    protected LocalDate createdOn;

    @Column(name = "updated_on")
    protected LocalDate updatedOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicEntity that = (BasicEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        if( id == null || id.equals("")){
            return super.hashCode();
        }
        return Objects.hash(getId());
    }
}
