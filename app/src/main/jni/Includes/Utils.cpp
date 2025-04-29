#include <jni.h>
#include <unistd.h>
#include <cstdio>
#include <cstring>
#include <string>
#include <cstdlib>
#include "Logger.hpp"
#include "ToastLength.hpp"

bool libLoaded = false;

jboolean isGameLibLoaded() {
    return libLoaded;
}
