-- Inserts ROL
INSERT INTO public.rol(
            id, descripcion, estado)
    VALUES ('ADMINISTRADOR', 'Administrador del Sistema', 'ACT');
INSERT INTO public.rol(
            id, descripcion, estado)
    VALUES ('GERENTE', 'Gerente', 'ACT');
    INSERT INTO public.rol(
            id, descripcion, estado)
    VALUES ('COORDINADOR', 'Coordinador', 'ACT');
    INSERT INTO public.rol(
            id, descripcion, estado)
    VALUES ('TRABAJADOR', 'Trabajador', 'ACT');
-- Inserts USUARIO
INSERT INTO public.usuario(
            identificacion, apellido, direccion, email, estado, nombre, password)
    VALUES ('1718891052', 'LOPEZ', '12 de octubre', 'calopezf@gmail.com', 'ACT', 'CRISTIAN', '12345');

-- Inserts USUARIO_ROL    
INSERT INTO public.usuario_rol(
            rol_id, identificacion)
    VALUES ('ADMINISTRADOR','1718891052');

-- Inserts PARAMETRO
--INSERT INTO public.parametro(
  --          codigo, descripcion, estado, nombre, tipo)
   -- VALUES ('RUTA_IMAGENES', 'Ruta de almacenamiento de imagenes en disco duro', 'ACT', 'Ruta Imagenes', 'CONF_GENERAL');
-- Inserts MEDICO_PACIENTE
 