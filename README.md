Servicio rest en javaEE con jboss 7.1 de server alojado en (sever.valdepeace.com) para la escucha de ficheros para su interpretacion en la ruta /ServiceFileCard/filecard metodo post.

El servicio tendra las dependencias:
<ul>
  <li>commons-fileupload-1.3.1.jar</li>
  <li>commons-io-2.4</li>
  <li>FileTGD-0.0.1.jar</li>
<ul>
commons de apache para el tratamiento de ficheros subidor con formdata.
FileTGD para la interpretacion de los ficheros.

Con la extension de chrome postman podremos probar el servicio:
  <ul>
  <li>POST http://server.valdepeace.com:8080/ServiceCardFile/filecard </li>
  <li>Resultado:<code>"{
    "nameFile": "C_ExxxxxV000000_E_20081007_0533.TGD",
    "lista_bloque": {
        "EF_PLACES": {
            "size": 1121,
            "placePointerNewestRecord": 83,
            "placeRecords": [],
            "noOfCardPlaceRecords": 0,
            "fid": "EF_PLACES"
        },
        "EF_ICC": {
            "size": 25,
            "clockStop": " 02",
            "cardExtendedSerialNumber": {
                "serialNumber": "283312",
                "monthYear": "0607",
                "type": " ff",
                </code>.......
  <li>GET http://server.valdepeace.com:8080/ServiceCardFile/filecard</li>
  <li>Resultado: "conexion establecida con servidor externo"</li>
  </ul>
