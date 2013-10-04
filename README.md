# meta-ezynq
***
This layer provides an unofficial support for Xilinx Zynq architecture 
as well as evaluation boards.

*ezynq-u-boot* creates bootable image without any extra proprietary tools required.
It does not support secure boot functionality and loading the FPGA (PL) part 
with a bitstream - it is planned to do under the control of the operating system - 
if needed this feature can be implemented using u-boot...
***
## Supported boards/machines
Boards supported by this layer and sub-layers:

* **Avnet MicroZed (Zynq)**
***
## Dependencies
This layer depends on:

* URI: *git://git.openembedded.org/bitbake*
* URI: *git://git.openembedded.org/openembedded-core*, layers: meta 
* URI: *git://github.com/MentorEmbedded/meta-sourcery.git*, layers: meta-sourcery (for external toolchains only)
* URI: *git://github.com/Xilinx/u-boot-xlnx.git*, branch=master-next
* URI: *git://git.code.sf.net/p/elphel/ezynq*
***
## Sub-layers

This repository contains the meta-ezynq layer as well as additional board
specific layers. Each of these board specific layers provide additional
configuration for the associated board. Please refer to the associated README
in each sub-layer for more details.

Note: sub-layers have no dependency between each other, all sub-layers can be
used at the same time without conflicts.
***
## Build instructions
The following instructions require a Poky installation (or equivalent).

Initialize a build using the *oe-init-build-env* script in Poky. Once
initialized configure *bblayers.conf* by adding the *meta-ezynq* layer as
well as any or all of the sub-layers of this repository, e.g.:

	meta-ezynq \
	meta-ezynq/meta-microzed \

To build a specific target BSP configure the associated machine in *local.conf*:
(See associated sub-layers for available machines and additional details)

	MACHINE ?= "microzed"

Build U-Boot:

	$ bitbake ezynq-u-boot

Once complete the images for the target machine will be available in the output
directory *tmp/deploy/images*.

Images generated:

* **boot.bin** (fsbl is not required to boot)

Extra output at *build/tmp/work/.../ezynq-u-boot/${PV}_${PR}/git/u-boot-xlnx/*:

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
