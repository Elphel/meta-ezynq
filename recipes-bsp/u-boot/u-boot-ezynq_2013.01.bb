DESCRIPTION = "EZYNQ is the Xilinx's bootgen (fsbl) replacement + u-boot. The output is a working BOOT.BIN"
HOMEPAGE = "http://elphel.com;http://blog.elphel.com"
SECTION = "bootloaders"

#Project Version
PV = "v2014.03"
#Project Revision
PR = "r0"

include u-boot-ezynq.inc

PROVIDES = "u-boot virtual/bootloader"