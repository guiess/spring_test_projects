package spring.hello_test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class DeviceDetectorController {

    @RequestMapping("/detect_device")
    public @ResponseBody String detectDevice(Device device){
        if(device.isNormal()) return "Normal";
        if(device.isMobile()) return "Mobile";
        if(device.isTablet()) return "Tablet";

        return null;
    }
}
