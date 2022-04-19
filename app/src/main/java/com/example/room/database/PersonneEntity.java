package com.example.room.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity(tableName="person")
public class PersonneEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String nom;

    @Ignore
    public PersonneEntity() {
    }

    @Ignore
    public PersonneEntity(int id, Date date, String nom) {
        this.id = id;
        this.date = date;
        this.nom = nom;
    }

    public PersonneEntity(Date date, String nom) {
        this.date = date;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String text) {
        this.nom = text;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + id +
                ", date=" + date +
                ", text='" + nom + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonneEntity that = (PersonneEntity) o;
        return id == that.id && Objects.equals(date, that.date) && Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, nom);
    }
}
