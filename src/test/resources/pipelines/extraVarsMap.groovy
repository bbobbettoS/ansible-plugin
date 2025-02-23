pipeline {
    agent {
        label('test-agent')
    }
    stages {
        stage('Create playbook') {
            steps {
                writeFile(encoding: 'UTF-8', file: 'playbook.yml', text: '''- hosts: localhost
  connection: local
  gather_facts: no
  tasks:
    - debug: msg=test
                ''')
                }
        }
        stage('Ansible playbook') {
            steps {
                warnError(message: 'ansible command not found?') {
                    ansiblePlaybook(
                        playbook: 'playbook.yml',
                        extraVars: [foo1: [value: 'bar1', hidden: false]],
                    )
                }
                warnError(message: 'ansible command not found?') {
                    ansiblePlaybook(
                        playbook: 'playbook.yml',
                        extraVars: [foo2: [value: 'bar2', hidden: true]],
                    )
                }
            }
        }
    }
}
