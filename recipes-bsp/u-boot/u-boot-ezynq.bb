DESCRIPTION = "EZYNQ is the Xilinx's bootgen (fsbl) replacement + u-boot. The output is a working BOOT.BIN"
HOMEPAGE = "http://elphel.com;http://blog.elphel.com"
SECTION = "bootloaders"

#Project Version
PV = "v2016.05"
#Project Revision
PR = "r0"

include u-boot-ezynq.inc

PROVIDES = "u-boot virtual/bootloader"

# Define the path to the xilinx platform init code/headers
PLATFORM_INIT_DIR ?= "/usr/src/xilinx-platform-init"

PLATFORM_INIT_STAGE_DIR = "${STAGING_DIR_HOST}${PLATFORM_INIT_DIR}"

#UBOOT_CONFIG="dtb"
