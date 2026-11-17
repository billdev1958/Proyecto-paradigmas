FUENTES := $(shell find src -name '*.java')
CLASSPATH := bin:lib/*

.PHONY: compilar ejecutar limpiar reiniciar

# make: compila solo si cambió algún .java
compilar: bin/.compilado

bin/.compilado: $(FUENTES)
	javac -cp "lib/*" -d bin $(FUENTES)
	@touch $@

# make ejecutar: compila si hace falta y abre el programa
ejecutar: compilar
	java --enable-native-access=ALL-UNNAMED -cp "$(CLASSPATH)" main.Main

# make limpiar: borra lo compilado
limpiar:
	rm -rf bin

# make reiniciar: borra la base de datos y las imágenes; se vuelven a crear los datos de ejemplo
reiniciar:
	rm -rf datos imagenes
