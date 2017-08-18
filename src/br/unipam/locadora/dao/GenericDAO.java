package br.unipam.locadora.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class GenericDAO<T> {
	
	@PersistenceContext
    protected EntityManager em;
    
    private final Class<T> persistentClass;
     
    public GenericDAO(Class<T> persistentClass){
        
        this.persistentClass = persistentClass;
      
    }
   
    public String salvar(T entity) {  
        try{
            em.merge(entity);
            return null;            
        }catch(Exception ex){
        	ex.printStackTrace();
            return "Erro: "+ex.getMessage();
        }
    }

    public String excluir(Serializable id) {
       try{
           T m = obter(id);
           em.remove(m);
           return null;
       }catch(Exception ex){
    	   ex.printStackTrace();
           return "Erro: "+ ex.getMessage();
       }
    }

    public T obter(Serializable id) {
       return em.find(persistentClass, id);
    }

    public List<T> todos() {

        TypedQuery<T> query 
           = em.createQuery("select c from "+ 
                    persistentClass.getSimpleName() +" as c", persistentClass);
        
        return query.getResultList();
    }
    
}
