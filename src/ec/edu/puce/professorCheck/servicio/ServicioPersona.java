package ec.edu.puce.professorCheck.servicio;
/**
 * PersonaservicioImpl.java
 * 
* Thu Oct 31 09:35:44 ECT 2013
 */
//package ec.gob.mrl.peti.service;
//
//import com.saviasoft.persistence.util.constant.CriteriaTypeEnum;
//import com.saviasoft.persistence.util.dao.GenericDao;
//import com.saviasoft.persistence.util.service.impl.GenericServiceImpl;
//import com.saviasoft.util.Criteria;
//import ec.gob.mrl.peti.dao.PersonaDao;
//import ec.gob.mrl.peti.dto.TodasLasFichasDto;
//import ec.gob.mrl.peti.model.Persona;
//import ec.gob.mrl.peti.model.Usuario;
//import java.util.Date;
//import java.util.List;
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//
//@Stateless(name = "personaServicio")
//public class PersonaServicioImpl extends GenericServiceImpl<Persona, Long>
//        implements PersonaServicio {
//
//    @EJB
//    private PersonaDao personaDao;
//
//    @Override
//    public GenericDao<Persona, Long> getDao() {
//        return personaDao;
//    }
//
//    @Override
//    public void buscaUltimaSecuencia() {
//        /*String[] criteriasAnd = {};
//         CriteriaTypeEnum[] typesAnd = {CriteriaTypeEnum.};
//         Object[] valuesCriteriaAnd = {};
//         String[] criteriasOrderBy = {};
//         boolean[] asc = {true};
//
//         Criteria criteria = new Criteria(criteriasAnd, typesAnd,
//         valuesCriteriaAnd, criteriasOrderBy, asc);
//
//         return this.findByCriterias(criteria);*/
//    }
//
//    @Override
//    public Persona recuperaXcedula(String cedula) {
//        String[] criteriasAnd = {"identificacion"};
//        CriteriaTypeEnum[] typesAnd = {CriteriaTypeEnum.STRING_EQUALS};
//        Object[] valuesCriteriaAnd = {cedula};
//        String[] criteriasOrderBy = {"id"};
//        boolean[] asc = {true};
//
//        Criteria criteria = new Criteria(criteriasAnd, typesAnd,
//                valuesCriteriaAnd, criteriasOrderBy, asc);
//
//        List<Persona> lista = findByCriterias(criteria);
//
//        if (lista != null && !lista.isEmpty()) {
//            return lista.get(0);
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public Long obtenerTotalListadoPaginado(String nombre, String apellido, String cedula, Long institucionId, Usuario usuarioLogueado, Date fechaNacDesde, Date fechaNacHasta, Long provinciaId) {
//        return personaDao.obtenerTotalListadoPaginado(nombre, apellido, cedula, institucionId, usuarioLogueado, fechaNacDesde, fechaNacHasta, provinciaId);
//    }
//
//    @Override
//    public List<Persona> obtenerListadoPaginado(String nombre, String apellido, String cedula, Long institucionId, Usuario usuarioLogueado,
//            int filas, int posicionInicial, Date fechaNacDesde, Date fechaNacHasta, Long provinciaId) {
//        return personaDao.obtenerListadoPaginado(nombre, apellido, cedula, institucionId, usuarioLogueado, filas, posicionInicial, fechaNacDesde, fechaNacHasta, provinciaId);
//    }
//
//    @Override
//    public Long obtenerTotalListadoPaginadoTf(String primerNombre, String segundoNombre, String apellidoPaterno, String apellidoMaterno, String cedula, Usuario usuarioLogueado, Date fechaNacDesde, Date fechaNacHasta, Date fechaFinDesde, Date fechaFinHasta) {
//        return personaDao.obtenerTotalListadoPaginadoTf(primerNombre, segundoNombre, apellidoPaterno, apellidoMaterno, cedula, usuarioLogueado, fechaNacDesde, fechaNacHasta, fechaFinDesde, fechaFinHasta);
//    }
//
//    @Override
//    public List<TodasLasFichasDto> obtenerListadoPaginadoTf(String primerNombre, String segundoNombre, String apellidoPaterno, String apellidoMaterno, String cedula, Usuario usuarioLogueado, int filas, int posicionInicial, Date fechaNacDesde, Date fechaNacHasta, Date fechaFinDesde, Date fechaFinHasta) {
//        return personaDao.obtenerListadoPaginadoTf(primerNombre, segundoNombre, apellidoPaterno, apellidoMaterno, cedula, usuarioLogueado, filas, posicionInicial, fechaNacDesde, fechaNacHasta, fechaFinDesde, fechaFinHasta);
//    }
//}
