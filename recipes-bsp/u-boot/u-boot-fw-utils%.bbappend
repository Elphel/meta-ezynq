include u-boot-ezynq.inc
inherit zynq7-platform-paths

PROVIDES = "u-boot virtual/bootloader"

do_compile_append() {
    cd ${GITDIR_uboot}
    oe_runmake env
    cd ${WORKDIR}
}

FILES_${PN} += "\
           /sbin/* \
          "

do_install_append(){
    install -d ${D}${base_sbindir}
    install -d ${D}${sysconfdir}
    install -m 755 ${S}/tools/env/fw_printenv ${D}${base_sbindir}/fw_printenv
    install -m 755 ${S}/tools/env/fw_setenv ${D}${base_sbindir}/fw_setenv
    install -m 0644 ${S}/tools/env/fw_env.config ${D}${sysconfdir}/fw_env.config
}