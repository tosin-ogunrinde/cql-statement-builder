#!/usr/bin/env groovy

node {
    properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '30', numToKeepStr: '10')), disableConcurrentBuilds(), pipelineTriggers([])])

    stage('checkout') {
        checkout scm
    }

    stage('clean') {
        sh './gradlew clean'
    }

    stage('sonarqube') {
        sh './gradlew sonarqube'
    }

    stage('uploadArchives') {
        sh './gradlew uploadArchives'
    }
}