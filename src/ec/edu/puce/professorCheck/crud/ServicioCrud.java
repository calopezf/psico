/*
 * Gestorinc S.A.
 * Sistema: Gestor G5
 * Creado:  15-oct-2012 - 12:01:02
 *
 * Los contenidos de este archivo son propiedad intelectual de Gestorinc S.A.
 * y se encuentran protegidos por la licencia: "GESTOR FIDUCIA/FONDOS G5".
 *
 * Usted puede encontrar una copia de esta licencia en:
 *   http://www.gestorinc.com
 *
 * Copyright 2008-2012 Gestorinc S.A. Todos los derechos reservados.
 */
package ec.edu.puce.professorCheck.crud;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * La clase ServicioUnidadesMedida define metodos para la gestion de la parametrizacion de Unidades de Medida.
 * @author Gestorinc S.A.
 * @version $Revision: 6464 $
 */
@Stateless
@LocalBean
public class ServicioCrud extends CrudServiceCore {
    /**
     * Log de auditor\u00eda.
     */
    private static final Logger LOGGER = Logger.getLogger(ServicioCrud.class.getSimpleName());

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
    public <T> T findByPK(Serializable pk, Class<T> claseEntidad, boolean... lock)  {
        return super.findByPK(pk, claseEntidad, lock);
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
    public <T> List<T> findOrder(T entityExample, String... orden) {
        return super.findOrder(entityExample, orden);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer count(Object entityExample) {
        return super.count(entityExample);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected Logger logger() {
        return LOGGER;
    }

}
