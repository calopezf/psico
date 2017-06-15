-- Alter tables
ALTER TABLE public.parametro
  ADD CONSTRAINT tipo_chkey CHECK (tipo='ESPECIALIDAD' or tipo='CONF_GENERAL');    
-- Inserts ROL
INSERT INTO public.rol(
            id, descripcion, estado)
    VALUES ('ADMINISTRADOR', 'Administrador del Sistema', 'ACT');
INSERT INTO public.rol(
            id, descripcion, estado)
    VALUES ('DOCTOR', 'Profesor', 'ACT');
    INSERT INTO public.rol(
            id, descripcion, estado)
    VALUES ('PACIENTE', 'Coordinador', 'ACT');
    INSERT INTO public.rol(
            id, descripcion, estado)
    VALUES ('ASISTENTE', 'Alumno', 'ACT');
-- Inserts USUARIO
INSERT INTO public.usuario(
            identificacion, apellido, direccion, email, estado, nombre, password)
    VALUES ('1010101010', 'LOPEZ', '12 de octubre', 'calopezf@gmail.com', 'ACT', 'CRISTIAN', '1010101010');
INSERT INTO public.usuario(
            identificacion, apellido, direccion, email, estado, nombre, password)
    VALUES ('1721930442', 'CORONEL', 'CONOCOTO', 'jvrcoronel@gmail.com', 'ACT', 'JAVIER', '1721930442');
INSERT INTO public.usuario(
            identificacion, apellido, direccion, email, estado, nombre, password, foto)
    VALUES ('1111111111', 'VILLALBA', 'BELLAVISTA', 'correo1@gmail.com', 'ACT', 'GABRIELA', 'abc123', 'C:/JC/fotosMedicus/mula.jpg');
INSERT INTO public.usuario(
            identificacion, apellido, direccion, email, estado, nombre, password, foto)
    VALUES ('2222222222', 'CORREA', 'MONTESERRIN', 'correo2@gmail.com', 'ACT', 'RAFAEL', 'abc123', 'C:/JC/fotosMedicus/mulo.jpg');
-- Inserts USUARIO_ROL    
INSERT INTO public.usuario_rol(
            rol_id, email)
    VALUES ('ADMINISTRADOR','calopezf@gmail.com');
INSERT INTO public.usuario_rol(
            rol_id, email)
    VALUES ('DOCTOR','jvrcoronel@gmail.com');
INSERT INTO public.usuario_rol(
            rol_id, email)
    VALUES ('PACIENTE','correo1@gmail.com');
INSERT INTO public.usuario_rol(
            rol_id, email)
    VALUES ('PACIENTE','correo2@gmail.com');    
-- Inserts PARAMETRO
INSERT INTO public.parametro(
            codigo, descripcion, estado, nombre, tipo)
    VALUES ('RUTA_IMAGENES', 'Ruta de almacenamiento de imagenes en disco duro', 'ACT', 'Ruta Imagenes', 'CONF_GENERAL');
-- Inserts MEDICO_PACIENTE
INSERT INTO public.medico_paciente(
            email_medico, email_paciente, estado)
    VALUES ('jvrcoronel@gmail.com', 'correo1@gmail.com', 'ACT');   
INSERT INTO public.medico_paciente(
            email_medico, email_paciente, estado)
    VALUES ('jvrcoronel@gmail.com', 'correo2@gmail.com', 'ACT');    