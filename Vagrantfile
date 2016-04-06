# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|
  config.vm.box = "ubuntu/wily64"

  # proxy stuff
  if Vagrant.has_plugin?("vagrant-proxyconf")
    # we create a private network for guest to host communication
    config.vm.network :private_network, ip: "192.168.2.2"
    config.apt_proxy.http = "http://192.168.2.1:3142/"
  end

  # share MockMock ports
  config.vm.network "forwarded_port", guest: 25, host: 2525
  config.vm.network "forwarded_port", guest: 80, host:8080

  # Enable provisioning with a shell script. Additional provisioners such as
  # Puppet, Chef, Ansible, Salt, and Docker are also available. Please see the
  # documentation for more information about their specific syntax and use.
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update
    sudo apt-get install -y openjdk-7-jre-headless
    sudo curl -o /opt/MockMock.jar https://raw.githubusercontent.com/tweakers-dev/MockMock/master/release/MockMock.jar 2>/dev/null
    sudo cp /vagrant/mockmock.service /etc/systemd/system/mockmock.service
    sudo systemctl daemon-reload
    sudo systemctl enable mockmock
    sudo systemctl start mockmock
  SHELL
end
