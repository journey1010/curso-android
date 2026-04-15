# Guía de Componentes y Analogías

Este documento describe el rol de cada pieza del rompecabezas en nuestra aplicación de CV, usando analogías de la vida real para facilitar su comprensión.

---

### 1. MainActivity (El Recepcionista / Formulario Vivo)
*   **Qué hace**: Es la puerta de entrada. Presenta el formulario de 2 pasos al usuario y orquestra el momento exacto en que los datos deben guardarse.
*   **Analogía**: Imagina a un recepcionista que te entrega una hoja de vida en blanco y te guía paso a paso. Cuando terminas, él mismo se encarga de llevarla al archivo central.

### 2. CvViewActivity (La Vitrina / Visualizador)
*   **Qué hace**: Recupera los datos guardados en la base de datos y los presenta de forma elegante al usuario.
*   **Analogía**: Es como una vitrina de trofeos donde puedes ver todos tus logros y fotos ya organizados.

### 3. CvFormViewModel (El Asistente de Memoria Corta)
*   **Qué hace**: Mantiene los datos que vas escribiendo mientras navegas entre los pasos. Si giras el teléfono, él no olvida lo que escribiste. No sabe nada de bases de datos permanentes.
*   **Analogía**: Es como un asistente con una libreta de notas rápida (post-it) que te recuerda qué escribiste en el paso anterior mientras decides qué más añadir.

### 4. CvValidator (El Inspector de Calidad)
*   **Qué hace**: Verifica que el email tenga el formato correcto, que el teléfono sea válido y que no dejes campos vacíos. Está inyectado con Hilt.
*   **Analogía**: Es el oficial de aduanas que revisa tu pasaporte. Si algo no está en orden, no te deja pasar al siguiente país (Paso 2).

### 5. AppDatabase (El Archivero Maestro)
*   **Qué hace**: Gestiona la instancia única de la base de datos SQLite (Room). Se encarga de abrir y cerrar el archivo de datos manualmente (Sin Hilt).
*   **Analogía**: Es el jefe del archivo del sótano. Solo él tiene la llave maestra para acceder a las estanterías de datos.

### 6. Entities (Las Fichas de Datos)
*   **Qué hace**: `PersonalInfoEntity` y `AcademicInfoEntity` definen cómo se guardan los datos.
*   **Analogía**: Son como los formularios pre-impresos con campos fijos (Nombre, Carrera, Año) que dictan dónde va cada pieza de información.

### 7. DAOs (Los Mensajeros de Datos)
*   **Qué hace**: Contienen las órdenes SQL (`Insert`, `Query`, `Delete`).
*   **Analogía**: Son los mensajeros que saben exactamente en qué cajón guardar la información y cómo buscarla cuando el jefe la pide.
