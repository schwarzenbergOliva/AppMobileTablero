# Información necesaria para consumir la API "Positive API"

## 📋 Datos que necesitas obtener de la API "Positive API"

Para poder consumir la API "Positive API", necesito la siguiente información:

### 1. **URL Base de la API**
   - Ejemplo: `https://api.positive.com/v1/` o `http://api.positive.com/`
   - ¿Cuál es la URL base completa?

### 2. **Endpoints disponibles**
   - ¿Qué endpoints tiene la API?
   - Ejemplos:
     - `GET /cards` - Obtener todas las cards
     - `POST /cards` - Crear una nueva card
     - `GET /cards/{id}` - Obtener una card específica
     - `PUT /cards/{id}` - Actualizar una card
     - `DELETE /cards/{id}` - Eliminar una card

### 3. **Autenticación**
   - ¿La API requiere autenticación?
   - Si es así, ¿qué tipo?
     - API Key (header)
     - Bearer Token (JWT)
     - Basic Auth
     - OAuth
   - ¿Cuál es el token/API key?

### 4. **Formato de datos**
   - ¿Qué formato usa? (JSON, XML, etc.)
   - ¿Cómo es la estructura de los datos que envía/recibe?
   - Ejemplo de respuesta JSON:
   ```json
   {
     "id": 1,
     "titulo": "Tarea ejemplo",
     "descripcion": "Descripción",
     "estado": "TODO"
   }
   ```

### 5. **Headers requeridos**
   - ¿Hay headers especiales que deba enviar?
   - Ejemplo: `Content-Type: application/json`, `Accept: application/json`

### 6. **Códigos de respuesta**
   - ¿Qué códigos HTTP usa?
   - 200 (éxito)
   - 201 (creado)
   - 400 (error)
   - 401 (no autorizado)
   - 404 (no encontrado)
   - 500 (error del servidor)

### 7. **Manejo de errores**
   - ¿Cómo devuelve los errores?
   - Ejemplo:
   ```json
   {
     "error": "Mensaje de error",
     "code": 400
   }
   ```

### 8. **Rate Limiting**
   - ¿Hay límites de peticiones por minuto/hora?
   - ¿Cuántas peticiones puedo hacer?

### 9. **Documentación**
   - ¿Tienes documentación de la API? (Swagger, Postman, etc.)
   - ¿Hay ejemplos de uso?

## 🔧 Lo que ya está configurado

✅ Dependencias de Retrofit instaladas
✅ Permisos de internet agregados
✅ Gson para serialización JSON

## 📝 Próximos pasos

Una vez que tengas esta información, puedo:
1. Crear la interfaz de Retrofit para la API
2. Configurar el cliente Retrofit con autenticación
3. Integrar las llamadas en tu `CardsRepository`
4. Agregar manejo de errores y estados de carga
5. Implementar caché si es necesario

## 💡 Ejemplo de lo que necesito

Si la API es algo como:
- URL: `https://api.positive.com/v1/`
- Endpoints:
  - `GET /tasks` - Obtener tareas
  - `POST /tasks` - Crear tarea
  - `PUT /tasks/{id}` - Actualizar tarea
- Autenticación: Bearer Token en header `Authorization: Bearer {token}`
- Formato: JSON

Con esa información puedo crear todo el código necesario.

