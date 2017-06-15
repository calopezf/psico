/*
 * Gestorinc S.A.
 * Sistema: Gestor G5
 * Creado: 23-09-2013
 *
 * Los contenidos de este archivo son propiedad intelectual de Gestorinc S.A.
 * y se encuentran protegidos por la licencia: "GESTOR FIDUCIA/FONDOS G5".
 *
 * Usted puede encontrar una copia de esta licencia en:
 *   http://www.gestorinc.com
 *
 * Copyright 2008-2013 Gestorinc S.A. Todos los derechos reservados.
 */
package ec.edu.puce.professorCheck.crud;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase abstracta que implementa las operaciones CRUD de BD con inyecci\u00f3n autom\u00e1tica la
 * auditor\u00eda, as\u00ed como b\u00fasquedas r\u00e1pidas utilizadas por los servicios.
 * Implementaci\u00f3n para el m\u00f3dulo core de G5.
 * @author Gestorinc S.A.
 * @version $Revision: $
 */
public abstract class CrudServiceCoreImpl extends CrudServiceImpl implements CrudService {

    /**
     * Objeto que maneja las operaciones de persistencia.
     */
    @PersistenceContext(name = "psicoDS")
    private EntityManager punit;

    /**
     * {@inheritDoc}
     * @deprecated NO se debe invocar directamente. utilizar el metodo
     *             getEntityManager().
     */
    @Deprecated
    @Override
    protected EntityManager getPunit() {
        return this.punit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Logger logger() {

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T findById(Object id, Class<T> claseEntidad, boolean... lock) {

        return super.findById(id, claseEntidad, lock);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> findAll(Class<T> clase, Integer... numLicencia) {

        return super.findAll(clase, numLicencia);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T findByPK(Serializable pk, Class<T> claseEntidad, boolean... lock) {

        return super.findByPK(pk, claseEntidad, lock);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(Object entity) {

        super.insert(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T insertReturn(T entity) {

        return super.insertReturn(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T update(T entity) {

        return super.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void remove(Serializable clavePrimaria, Class<T> claseEntidad) {

        super.remove(clavePrimaria, claseEntidad);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> find(Integer numLicencia, T entityExample, Boolean... maxRegistrosConsulta) {

        return super.find(numLicencia, entityExample, maxRegistrosConsulta);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> findOrder(T entityExample, String... orden) {

        return super.findOrder(entityExample, orden);
    }

    /**
     * Retorna una referencia a la clase utilitaria para genera querys dinamicos.
     * @return
     */
    protected QueryBuilder getQueryBuilder() {
        return this.qryBuilder;
    }

}
