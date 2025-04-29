#include <cstdlib>
#include <sys/system_properties.h>

int api_level() {
    char value[92] = { 0 };
    if (__system_property_get("ro.build.version.sdk", value) < 1) return -1;

    try {
        return std::stoi(value);
    } catch (const std::invalid_argument& e) {
        return -1;
    } catch (const std::out_of_range& e) {
        return -1;
    }
}
