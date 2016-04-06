# HEIGVD-RES-2016-Labo-SMTP

## What's this about ?
A simple SMTP client created in the RES course at HEIG-VD in order to become familiar with the protocol.

The idea of this project is to send a forged e-mail to given victims. The content of the e-mail is selected among a set of pre-defined messages.

The groups of victims are formed from a list of e-mails. In every group of victims, there are 1 sender and at least 2 recipients (i.e. the minimum size for a group is 3). The mail is sent to all group recipients, from the address of the group sender.

## Installation and usage

Dependencies: [vagrant](https://www.vagrantup.com/) (for the mock server, optional) and a version of the [JDK/JRE](https://www.oracle.com/technetwork/java/javase/downloads/index.html) (for the client).

Before anything, begin by downloading or closing the sources.

### Mock server deployment (with vagrant)
1. Go to root folder (where the .vagrant file is) and type `$ vagrant up`.
2. That's it.

### Mock server deployment (without vagrant)
1. 

### Client installation and usage
1. Compile and run the client.
2. You will have to provide two arguments: the file containing target e-mails and the number of groups you want to create.
3. That's it, you just sent your forged e-mails.
