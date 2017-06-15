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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase abstracta que implementa las operaciones CRUD de BD con inyecci\u00f3n autom\u00e1tica la
 * auditor\u00eda, as\u00ed como b\u00fasquedas r\u00e1pidas utilizadas por los servicios.
 * Implementaci\u00f3n para el m\u00f3dulo core de G5.
 * @author Gestorinc S.A.
 * @version $Revision: $
 */
public abstract class CrudServiceCore extends CrudServiceImpl {

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
    @Override
    public EntityManager getPunit() {
        return this.punit;
    }

    /**
     * Retorna una referencia a la clase utilitaria para genera querys dinamicos.
     * @return
     */
    protected QueryBuilder getQueryBuilder() {
        return this.qryBuilder;
    }

}
