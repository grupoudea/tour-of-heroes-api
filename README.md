# Dockerizar y desplegar en AWS ECS API REST spring boot usando Github Actions

Está REST api se conecta a una base de datos alojada en AWS RDS y su despliegue está automatizado con Github Actions (CI/CD).

Características del repo:

- [x] Lombok - Mapper
- [x] Manejo de excepciones con spring boot
- [x] Pruebas unitarias (no todo está cubierto)
- [x] Conexión con Base de datos (RDS de AWS)
- [x] Despliegue automatizado con Github Actions hacia ECS
- [x] Guia para dockerización
- [ ] Implementación de swagger
- [ ] Guia para despliegue en ECS
- [ ] Guia para configurar github actions

url en aws: http://ec2-44-214-2-56.compute-1.amazonaws.com:49153/api/hero


# Guias para lograr despliegue en ECS con GitHub Actions
**Contenido**

1. [Endpoints](#id1)
2. [Dockerizar](#id2)
3. [Configurar AWS ECS](#id3)
4. [Configurar IAM](#id4)
5. [Github Actions](#id5)

## Endpoints<a name="id1"></a>

## Dockerizar<a name="id2"></a>

### Forma 1:

Crear un archivo Dockerfile como el siguiente: 

```bash
# la imagen base será desde alpine (es una imagen linux livina)
FROM adoptopenjdk/openjdk11:alpine-jre

# creamos un argumento con el valor de la ruta donde se crea nuestro .jar
ARG JAR_FILE=./build/libs/tour-of-heroes-api.jar

# cd /op/app
WORKDIR /opt/app

# copiamos el o los archivos, en este caso nuestro jar lo copiamos en /opt/app
COPY ${JAR_FILE} "app.jar"

# así arrancará nuestro container
ENTRYPOINT ["java", "-jar", "app.jar"]
```
ya tenemos nuestro Dockerfile, ahora construyamos la imagen:

```bash
# el punto (.) al final indica que en el directorio actual está el Dockerfile con el que haremos el build.
docker build --tag $IMAGE_NAME --build-arg PASS_DB=${MY_PASS_DB} \ 
--build-arg USER_DB=${MY_USER_DB} --build-arg HOST_DB=${MY_HOST_DB} .
```

Finalmente podemos iniciar nuestro contenedor:

```bash
docker run --name tour-of-heroes-api-i-con -d -p 8080:8080 tour-of-heroes-api-i:latest
```

Note que al contenedor le paso unas variables de entorno que está definidas como variables de entorno del sistema 
operativo host. Esas variables de entorno son usadas para la contraseña, usuario y host de la base de datos. 
Es una forma para evitar exponer las credenciales. 

### Forma 2:

Creamos un archivo Dockerfile igual que el anterior y adicionamos scripts utilitarios que nos agilizaran el desarrollo. 
Es decir, cada vez que queramos probar una nueva version de la app desde la dockerización tendremos que parar el contenedor,
removerlo y remover la imagen, compilar el jar, ejecutar nuevamente el build de la imagen. Para evitar hacer esto, creamos
un script que lo haga. 

*build-docker.sh*

```bash
echo "Starting..."

# Definimos nuestras variables
IMAGE_NAME="tour-of-heroes-api-i"
CONTAINER_NAME="$IMAGE_NAME-con"

echo $IMAGE_NAME
echo $CONTAINER_NAME

echo "Stoping container $CONTAINER_NAME"

# validamos que la salida del comando docker stop <container-name> sea un error, si lo es, quizá no exista el contenedor
if docker stop $CONTAINER_NAME 2>&1 | grep -q "No such"; then
  echo "Error trying stop container $CONTAINER_NAME maybe container does not exist"
else
  echo "$CONTAINER_NAME has been stopped. Good job!"
fi

# validamos que la salida del comando docker rm <container-name> sea un error, si lo es, quizá no exista el contenedor
echo "Removing container $CONTAINER_NAME"
if docker rm $CONTAINER_NAME 2>&1 | grep -q "No such"; then
  echo "Error trying remove container $CONTAINER_NAME maybe container does not exist"
else
  echo "$CONTAINER_NAME has been removed. Good job!"
fi

# validamos que la salida del comando docker rmi <image-name> sea un error, si lo es, quizá no exista la imagen
echo "Removing image $IMAGE_NAME"
if docker rmi $IMAGE_NAME 2>&1 | grep -q "No such"; then
  echo "Error trying remove image $IMAGE_NAME maybe image does not exist"
else
  echo "$IMAGE_NAME has been removed. Good job!"
fi

# compilamos nuestro proyecto para generar el .jar
./gradlew clean build -x test


# construimos la imagen con el Dockerfile
docker build --tag $IMAGE_NAME --build-arg PASS_DB=${MY_PASS_DB} --build-arg USER_DB=${MY_USER_DB} --build-arg HOST_DB=${MY_HOST_DB} .
```

podemos ejecutar:

```bash
sh ./scripts/build-docker.sh
```


Ahora, para iniciar nuestro contenedor nos apoyamos en el archivo utilitario:

*docker-start.sh* en **/scripts/docker-start.sh**

que contiene: 
```bash
docker run --name tour-of-heroes-api-i-con -d -p 8080:8080 tour-of-heroes-api-i:latest
```

podemos ejecutar:

```bash
sh ./scripts/docker-start.sh
```

## Configurar AWS ECS<a name="id3"></a>

Se asume que ya tenemos cuenta en AWS

### Paso 1: Crear nuestro repo en ECR
```
todo
```
### Paso 2: Crear task en ECS
```
todo
```
### Paso 3: Crear Cluster en ECS
```
todo
```
### Paso 4: Crear service en ECS
```
todo
```
## Configurar IAM<a name="id4"></a>

### Paso 1: Crear usuario en IAM con permisos especificos
```
todo
```

## Github actions<a name="id5"></a>

### Paso 1: Configurar secrets
```
todo
```

### Paso 2: Configurar workflow de despliegue en ECS
```
todo
```

