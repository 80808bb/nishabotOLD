# nishabot
<p>Bot de discord que envía fotos aleatorias de Nisha, (y otras cosas).</p>

<p>El bot está escrito en Java usando usando el wrapper JDA y está pensado para un solo servidor (guild).</p>

<h2>Estructura de ficheros/información adicional:</h2>
<p>Todo lo escrito en esta sección es susceptible de cambios.</p>
<h4>Directorios y ficheros</h4>
<p>La estructura de los directorios es la siguiente:
<br>
  <ul>
    <li><b>/ficheros</b> - Aquí se encuentran los ficheros utilizados para almacenar datos como por ejemplo  el fichero ultimoNegro.txt</li>
    <li><b>/nishafotos</b> - Aquí se encuentran todas las fotos de Nisha</li>
     <li><b>/fotosRandom</b> - Aquí se encuentran todas las fotos que no sean de Nisha (a excepción de la foto del negro, que se encuentra en /nishafotos)</li>
  </ul>
</p>
<h4>config.xml</h4>
<p>Fichero XML de configuración almacenado en el directorio raíz que contiene diferentes parámetros que se suelen cambiar frecuentemente.
<br>
Antes de iniciar sesión, el bot lee este fichero de configuración.
<br>
Contenidos:
</p>
<ul>
  <li>Token del bot.</li>
  <li>ID de la guild de los chavales (O la guild dónde se testea el bot).</li>
  <li>La cantidad de fotos de nisha que hay.</li>
  <li>El número de la foto del negro en la caja.</li>
</ul>
<h4>ultimoNegro.txt</h4>
<p>En este fichero se guarda el id del usuario al que le haya tocado el negro y el tiempo en milisegundos de cuando le ha tocado.<br>
Por ejemplo:
</p>
<p>
  184321136320756224 1525179464193
</p>
<h4>rankNegros.txt</h4>
<p>Este fichero sirve para almacenar los id de todos los usuarios que han sido "campeón del negro" y el tiempo que han durado siéndolo.
<br>
Por ejemplo:
</p>
<p>
  169170722990035969 266977598 174180243746534912 187382016 241738464507926529 152434263
</p>
<p>
  (El usuario con id 169170722990035969 ha sido campeón del negro durante 266977598 milisegundos, el 174180243746534912 lo ha sido durante 187382016 milisegundos, etc.)
</p>
<p>El tiempo en milisegundos es la suma de todas las veces que dicho usuario ha sido el campeón del negro.</p>
<h4>Fotos de Nisha</h4>
<p>
  El nombre de las fotos es un número (desde el 1 hasta el número total de fotos) con un formato .jpg (en minúsculas).
  <br>
  Se van añadiendo fotos de Nisha frecuentemente por lo que el número total de fotos se establece en el fichero de configuración, también podra implementarse un método que cuente la cantidad de fotos cada vez que se encienda el bot.
</p>
<h2>Dependencias:</h2>
<br>
<ul>
  <li>JDA</li>
  <ul>
    <li>Versión: 3.6.0</li>
    <li><a href="https://github.com/DV8FromTheWorld/JDA">Github</a>
  </ul>
</ul>
