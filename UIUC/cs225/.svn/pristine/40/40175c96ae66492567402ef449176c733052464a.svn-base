EXENAME = testimage
OBJS = png.o rgbapixel.o testimage.o image.o

EXENAME2 = testscene
OBJS2 = png.o rgbapixel.o image.o testscene.o scene.o

CXX = clang++
CXXFLAGS = -std=c++1y -stdlib=libc++ -c -g -O0 -Wall -Wextra -pedantic
LD = clang++
LDFLAGS = -std=c++1y -stdlib=libc++ -lpng -lc++abi

all : $(EXENAME) $(EXENAME2)

$(EXENAME) : $(OBJS)
	$(LD) $(OBJS) $(LDFLAGS) -o $(EXENAME)

$(EXENAME2) : $(OBJS2)
	$(LD) $(OBJS2) $(LDFLAGS) -o $(EXENAME2)

rgbapixel.o : rgbapixel.cpp rgbapixel.h
	$(CXX) $(CXXFLAGS) rgbapixel.cpp

png.o : png.cpp png.h 
	$(CXX) $(CXXFLAGS) png.cpp

testimage.o : testimage.cpp
	$(CXX) $(CXXFLAGS) testimage.cpp

image.o : image.cpp image.h
	$(CXX) $(CXXFLAGS) image.cpp

testscene.o : testscene.cpp
	$(CXX) $(CXXFLAGS) testscene.cpp

scene.o : scene.cpp scene.h
	$(CXX) $(CXXFLAGS) scene.cpp


clean :
	-rm -f *.o $(EXENAME)
