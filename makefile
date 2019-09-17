NAME = "Main"

all:
	# (a bit of a hack to compile everything each time ...)
	@echo "Compiling..."
	javac -cp vecmath.jar *.java

run: all
	@echo "Running..."
	java -cp "vecmath.jar:." $(NAME)

clean:
	rm -rf *.class
