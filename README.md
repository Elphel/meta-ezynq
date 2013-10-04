# meta-ezynq
***
This layer provides an unofficial support for Xilinx Zynq architecture 
as well as evaluation boards.

*ezynq-u-boot* creates bootable image without any extra proprietary tools required.
It does not support secure boot functionality and loading the FPGA (PL) part 
with a bitstream - it is planned to do under the control of the operating system - 
if needed this feature can be implemented using u-boot.

***
## Supported boards/machines
Boards supported by this layer:

* **Avnet MicroZed (Zynq)**

## Dependencies
This layer depends on:

* URI: *git://git.openembedded.org/bitbake*
* URI: *git://git.openembedded.org/openembedded-core*, layers: meta 
* URI: *git://github.com/MentorEmbedded/meta-sourcery.git*, layers: meta-sourcery (for external toolchains only)
* URI: *git://github.com/Xilinx/u-boot-xlnx.git*, branch=master-next
* URI: *git://git.code.sf.net/p/elphel/ezynq*

## Build instructions
The following instructions require a Poky installation (or equivalent).

Initialize a build using the *oe-init-build-env* script in Poky. Once
initialized configure *bblayers.conf* by adding the *meta-ezynq* layer:

	meta-ezynq \

To build a specific target BSP configure the associated machine in *local.conf*:

	MACHINE ?= "microzed"

Build U-Boot:

	$ bitbake ezynq-u-boot

Once complete the images for the target machine will be available in the output
directory *tmp/deploy/images*.

Images generated:

* **boot.bin** (fsbl is not required to boot)

Extra output at *build/tmp/work/.../ezynq-u-boot/${PV}\_${PR}/git/u-boot-xlnx/*:

* **u-boot.html** - configuration settings overview
* **u-boot.map** - the bootloader's memory map
* **u-boot** - ELF file

## Booting

### SD Card Boot
**Note:** This boot flow generates a bootable **boot.bin**.

1. Copy **boot.bin** onto the SD Card.
* (might not work) Copy the following to the SD card (ensure to rename the files where appropriate):
    *  **uImage** - kernel
    *  **uramdisk.image.gz** (core-image-minimal-<machine name>.ext2.gz.u-boot) - RootFS
    *  **devicetree.dtb** (uImage-<machine name>.dtb) - Device Tree Blob
* Insert SD Card, connect UART to Terminal program and boot board (Ensure the
board is configured for SD Boot).
* Once in u-boot command line - to boot *uImage* type:

        ezynq> boot


## Develop (add new machine/board)

1. Configure the new board in *build/conf/local.conf*:

        MACHINE ?= "<new-board>"

* Create *conf/machine/<new-board>.conf* - use *microzed.conf* as a reference.

* Edit *<new-board>.conf* - add:

        UBOOT_MACHINE = "<new-config-name>_config"
 
 where *<new-config-name>* is from *boards.cfg* in [u-boot-xlnx](https://github.com/Xilinx/u-boot-xlnx) plus *"_config"*-ending

* 

        bitbake ezynq-u-boot -c patch -f

 The command clones git repositories and add ezynq files to u-boot-xlnx.

 **Note:** Ezynq can be cloned and generate **boot.bin** apart from poky - please see Ezynq's documentation.

* Edit ezynq files located at *build/tmp/work/.../ezynq-u-boot/${PV}_${PR}/git/u-boot-xlnx/*:

 * *boards.cfg* - add <new-config-name> if it's not in the list.

 * create a new *include/configs/<new-config-name>.h* - use zynq_microzed.h as a reference)

 * *<new-config-name>.h* needs to include the following files:

            #include <configs/ezynq/ezynq_MT41K256M16RE125.h>  /* should be before zed_ezynq.h as it overwrites DDR3L with DDR3 */
            #include <configs/ezynq/ezynq_XC7Z010_1CLG400.h>
            #include <configs/ezynq/zed_ezynq.h>

 * edit the included header files according to the new board configuration.

 **WARNING:** After the step 5 **don't** run *bitbake ezynq-u-boot -c clean* -
deletes everything in *build/tmp/work/.../ezynq-u-boot/${PV}_${PR}/*

7. 

        bitbake ezynq-u-boot

